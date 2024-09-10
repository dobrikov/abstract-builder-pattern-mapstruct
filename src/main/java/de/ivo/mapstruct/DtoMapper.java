package de.ivo.mapstruct;

import org.mapstruct.Mapper;
import de.ivo.abstractbuilderpattern.BigIntegerDto;
import de.ivo.abstractbuilderpattern.StringDto;

@Mapper
public interface DtoMapper {
  StringDto clone(StringDto stringDto);

  BigIntegerDto clone(BigIntegerDto bigIntegerDto);
}
