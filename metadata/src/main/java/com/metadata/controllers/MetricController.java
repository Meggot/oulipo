// Copyright (c) 2019 Travelex Ltd

package com.metadata.controllers;

import com.common.models.dtos.MetricDto;
import com.common.models.exceptions.EntityValidationException;
import com.common.models.responses.EntityModificationResponse;
import com.metadata.controllers.assemblers.MetricAssembler;
import com.metadata.dao.entites.Metric;
import com.metadata.dao.repository.MetricRepository;
import com.metadata.services.MetricManagementService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricController {

    @Autowired
    MetricRepository metricRepository;

    @Autowired
    MetricAssembler metricAssembler;

    @Autowired
    MetricManagementService metricManagementService;

    @ResponseBody
    @GetMapping("/{metricId}")
    public Resource<MetricDto> findByMetricId(@PathVariable("metricId") Metric metric, Model model) {
        Resource<MetricDto> metricDtoResource = new Resource<>(metricAssembler.toResource(metric));
        model.addAttribute("metric", metricDtoResource);
        return metricDtoResource;
    }

    @ResponseBody
    @GetMapping
    public HttpEntity<PagedResources<MetricDto>> findAllMetrics(@QuerydslPredicate(root = Metric.class) Predicate predicate,
                                                                Pageable pageable,
                                                                Model model,
                                                                PagedResourcesAssembler assembler) {
        Page<MetricDto> page = metricRepository.findAll(predicate, pageable).map(entity -> metricAssembler.toResource((Metric) entity));
        model.addAttribute("metrics", page);
        return new ResponseEntity<>(assembler.toResource(page), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping
    public Resource<MetricDto> postMetric(@RequestBody MetricDto metricDto) {
        EntityModificationResponse<Metric> metricDtoEntityModificationResponse = metricManagementService.createMetric(metricDto);
        if (metricDtoEntityModificationResponse.isError()) {
            throw new EntityValidationException(metricDtoEntityModificationResponse.getApiSubErrors(),
                                                "Metric failed to post due to validation issues.");
        }
        return new Resource<>(metricAssembler.toResource(metricDtoEntityModificationResponse.getEntity()));
    }
}
