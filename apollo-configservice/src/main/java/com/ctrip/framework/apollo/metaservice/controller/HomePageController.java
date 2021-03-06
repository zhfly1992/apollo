package com.ctrip.framework.apollo.metaservice.controller;

import com.ctrip.framework.apollo.core.ServiceNameConsts;
import com.ctrip.framework.apollo.core.dto.ServiceDTO;
import com.ctrip.framework.apollo.metaservice.service.DiscoveryService;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * For non-eureka discovery services such as kubernetes and nacos, there is no eureka home page, so we need to add a default one
 */
@Profile({"kubernetes", "nacos-discovery"})
@RestController
public class HomePageController {
  private final DiscoveryService discoveryService;

  public HomePageController(DiscoveryService discoveryService) {
    this.discoveryService = discoveryService;
  }

  @GetMapping("/")
  public List<ServiceDTO> listAllServices() {
    List<ServiceDTO> allServices = Lists.newLinkedList();
    allServices
        .addAll(discoveryService.getServiceInstances(ServiceNameConsts.APOLLO_CONFIGSERVICE));
    allServices.addAll(discoveryService.getServiceInstances(ServiceNameConsts.APOLLO_ADMINSERVICE));

    return allServices;
  }
}
