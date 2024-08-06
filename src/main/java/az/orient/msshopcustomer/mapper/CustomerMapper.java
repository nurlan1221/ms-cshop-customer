package az.orient.msshopcustomer.mapper;


import az.orient.msshopcustomer.dto.Customerdto;
import az.orient.msshopcustomer.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerEntity toCustomerEntity(Customerdto customerdto);

    Customerdto toCustomerdto(CustomerEntity customerentity);

    List<Customerdto> toCustomerdtoList(List<CustomerEntity> customerentities);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    CustomerEntity updateCustomerEntity(Customerdto customerdto, @MappingTarget CustomerEntity customerentity);


}
