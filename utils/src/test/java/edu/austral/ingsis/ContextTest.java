package edu.austral.ingsis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContextTest {

  private static final Context context = new Context();

  @Test
  public void testAddVariable() {
    Variable toAdd = new ConcreteVariable("y", new VariableType("string", 3));
    context.addVariable(toAdd);

    Assertions.assertTrue(context.getVariables().contains(toAdd));
  }

  @Test
  public void testUpdateVariable() {
    Variable toAdd = new ConcreteVariable("z", new VariableType("number", 3));
    context.addVariable(toAdd);
    toAdd.setValue("6");
    context.updateVariable(toAdd);

    Assertions.assertTrue(context.getVariables().contains(toAdd));
  }

  @Test
  public void testGetVariableType() {
    Variable toAdd = new ConcreteVariable("y", new VariableType("string", 3));
    context.addVariable(toAdd);

    Assertions.assertTrue(context.getVariables().contains(toAdd));
  }
}
