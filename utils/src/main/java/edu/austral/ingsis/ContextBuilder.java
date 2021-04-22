package edu.austral.ingsis;

import java.util.function.Consumer;

public class ContextBuilder {

  private Context context;
  private VariableBuilder toAdd = new VariableBuilder();

  public ContextBuilder(Context context) {
    this.context = context;
  }

  public ContextBuilder() {
    context = new Context();
    context = context.setContexts();
  }

  public Context build() {
    Variable newVariable = toAdd.build();
    if (context.checkVariable(newVariable)) context.updateVariable(newVariable);
    toAdd = new VariableBuilder();
    return context;
  }

  public String getToAddValue() {
    return toAdd.build().getValue();
  }

  public boolean toAddExists() {
    Variable add = toAdd.build();
    return add != null
        && (!add.getValue().isEmpty() || !add.getType().isEmpty() || !add.getName().isEmpty());
  }

  public boolean toAddIsConst() {
    return toAdd.build().isConst();
  }

  public ContextBuilder addVariable(VariableBuilder toAdd) {
    this.toAdd = toAdd;
    return this;
  }

  public ContextBuilder setToAddName(String name) {
    toAdd.withName(name);
    return this;
  }

  public ContextBuilder setToAddType(VariableType type) {
    toAdd.withType(type);
    return this;
  }

  public ContextBuilder setToAddValue(String value) {
    toAdd.withValue(value);
    return this;
  }

  public ContextBuilder setToAddIsConst(boolean value) {
    toAdd.asConst(value);
    return this;
  }

  public ContextBuilder setNextExecute(Runnable runnable) {
    context.setNextExecute(new ExecutingCommand(runnable));
    return this;
  }

  public VariableType getVariableType(String name) {
    return context.getVariableType(name);
  }

  public String getVariableValue(String name) {
    return context.getVariableValue(name);
  }

  public Variable getVariable(String name) {
    return context.getVariable(name);
  }

  public void emptySubContextIf() {
    context.getSubContextIf().empty();
  }

  public void emptySubContextElse() {
    context.getSubContextElse().empty();
  }

  public Consumer<String> getOut() {
    return context.getOut();
  }
}
