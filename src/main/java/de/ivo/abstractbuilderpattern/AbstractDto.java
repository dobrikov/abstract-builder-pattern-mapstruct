package de.ivo.abstractbuilderpattern;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof AbstractDto that))
      return false;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }

  @Override
  public String toString() {
    return "ActivityVariableDto{" + "modifikationsId=" + id + ", name='" + name + '\'' + '}';
  }
}
