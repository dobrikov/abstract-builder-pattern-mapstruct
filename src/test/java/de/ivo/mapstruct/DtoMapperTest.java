package de.ivo.mapstruct;

import java.math.BigInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import de.ivo.abstractbuilderpattern.BigIntegerDto;
import de.ivo.abstractbuilderpattern.StringDto;

public class DtoMapperTest {

  @Test
  void testString() {
    StringDto dto = StringDto.builder().id(1).value("value").name("name").build();

    DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);
    StringDto dtoClone = dtoMapper.clone(dto);

    Assertions.assertEquals(dto.getValue(), dtoClone.getValue());
    Assertions.assertEquals(dto.getId(), dtoClone.getId());
    Assertions.assertEquals(dto.getName(), dtoClone.getName());
  }

  @Test
  void testBigInt() {
    BigIntegerDto dto = BigIntegerDto.builder().id(1).value(BigInteger.ONE).name("name").build();

    DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);
    BigIntegerDto dtoClone = dtoMapper.clone(dto);

    Assertions.assertEquals(dto.getValue(), dtoClone.getValue());
    Assertions.assertEquals(dto.getId(), dtoClone.getId());
    Assertions.assertEquals(dto.getName(), dtoClone.getName());
  }

}
