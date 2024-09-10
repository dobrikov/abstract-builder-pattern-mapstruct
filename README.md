# abstract-builder-pattern-mapstruct

This Java project shows a use case of the abstract builder pattern adapted from the book "Effective Java" by Joshua Bloch.  
It aims to show missing functionality/bug of the mapstruct framework version 1.6.0. The use case:

We have an abstract class 'AbstractDto' with an abstract builder pattern for building the objects.

```Java
public abstract class AbstractDto {
  private final String name;
  private final Integer id;

  public abstract static class Builder<T> {
    private String name;
    private Integer id;

    protected abstract T self();

    public T name(String name) {
      this.name = name;
      return self();
    }

    public T id(Integer id) {
      this.id = id;
      return self();
    }

    public abstract AbstractDto build();
  }

  protected AbstractDto(Builder<?> builder) {
    this.name = builder.name;
    this.id = builder.id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }
}
```

Then we have two concrete implementations 'StringDto' and 'BigIntegerDto' of the abstract class 'AbstractDto':

```Java
public class StringDto extends AbstractDto {
  private final String value;
  public static class Builder extends AbstractDto.Builder<Builder> {

    private String value;

    @Override
    protected Builder self() {
      return this;
    }

    public Builder value(String value) {
      this.value = value;
      return self();
    }

    public Builder name(String value) {
      return super.name(value);
    }

    public Builder id(Integer value) {
      return super.id(value);
    }

    @Override
    public StringDto build() {
      return new StringDto(this);
    }
  }

  private StringDto(Builder builder) {
    super(builder);
    value = builder.value;
  }

  public String getValue() {
    return value;
  }

  public static Builder builder() {
    return new Builder();
  }
}
```

and 

```Java
public class BigIntegerDto extends AbstractDto {
  private final BigInteger value;

  public static class Builder extends AbstractDto.Builder<Builder> {

    private BigInteger value;

    @Override
    protected Builder self() {
      return this;
    }

    public Builder value(BigInteger value) {
      this.value = value;
      return self();
    }

    @Override
    public BigIntegerDto build() {
      return new BigIntegerDto(this);
    }
  }

  private BigIntegerDto(Builder builder) {
    super(builder);
    value = builder.value;
  }

  public BigInteger getValue() {
    return value;
  }

  public static Builder builder() {
    return new Builder();
  }
}
```

Both classes have their own concrete builders that extend the abstract builder in 'AbstractDto'. 
The difference between the two classes is that the 'StringDto' builder class overrides the methods 'name(String name)' and 'id(Integer id)' of 
the abstract builder (from the client view these two builder methods are redundant, because they are already implemented
by the abstract builder class). In the case of 'BigIntegerDto' these methods are not overridden.

In this project we are using Mapstruct to clone both concrete classes:

```Java
@Mapper
public interface DtoMapper {
  StringDto clone(StringDto stringDto);

  BigIntegerDto clone(BigIntegerDto bigIntegerDto);
}
```

When we look at the generated code of DtoMapperImpl, then we can observe that the clone method for 'BigIntegerDto' is
not complete, because the mappings `bigIntegerDto1.name( bigIntegerDto.getName() );` and `bigIntegerDto1.id( bigIntegerDto.getId() );`
are missing.

```Java
public class DtoMapperImpl implements DtoMapper {
    @Override
    public StringDto clone(StringDto stringDto) {
        if ( stringDto == null ) {
            return null;
        }
        StringDto.Builder stringDto1 = StringDto.builder();

        stringDto1.value( stringDto.getValue() );
        stringDto1.name( stringDto.getName() );
        stringDto1.id( stringDto.getId() );

        return stringDto1.build();
    }

    @Override
    public BigIntegerDto clone(BigIntegerDto bigIntegerDto) {
        if ( bigIntegerDto == null ) {
            return null;
        }
        BigIntegerDto.Builder bigIntegerDto1 = BigIntegerDto.builder();

        bigIntegerDto1.value( bigIntegerDto.getValue() );

        return bigIntegerDto1.build();
    }
}
```

There is a missing functionality or a bug in Mapstruct 1.6.0, because we expect that the mapstruct generator should find the
mappings `bigIntegerDto1.name( bigIntegerDto.getName() );` and `bigIntegerDto1.id( bigIntegerDto.getId() );` in the abstract 
builder pattern class and place them in the generated code.