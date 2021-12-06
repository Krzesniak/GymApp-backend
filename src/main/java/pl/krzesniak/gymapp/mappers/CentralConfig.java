package pl.krzesniak.gymapp.mappers;

import org.mapstruct.Builder;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface CentralConfig {
}
