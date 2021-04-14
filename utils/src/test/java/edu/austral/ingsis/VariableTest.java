package edu.austral.ingsis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VariableTest {

  @Test
  public void testBuildingAVariable() {
    VariableBuilder builder = new VariableBuilder();
    VariableType type = new VariableType("string", 3);
    Variable expected = new ConcreteVariable("x", type);
    expected.setValue("hola");

    Variable actual = builder.setName("x").setType(type).setValue("hola").build();
    Assertions.assertEquals(expected.getName(), actual.getName());
    Assertions.assertEquals(expected.getType(), actual.getType());
    Assertions.assertEquals(expected.getValue(), actual.getValue());

    Variable newActual = builder.setType("string", 3).build();
    Assertions.assertEquals(type.getName(), newActual.getType().getName());
    Assertions.assertEquals(type.getOrdinal(), newActual.getType().getOrdinal());
  }
}
