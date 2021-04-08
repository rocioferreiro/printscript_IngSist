package edu.austral.ingsis;

public class ContextBuilder {

  private Context context;
  private VariableBuilder toAdd = new VariableBuilder();

  public ContextBuilder(Context context) {
    this.context = context;
  }

  public ContextBuilder() {
    context = new Context();
  }

  public Context build() {
    Variable newVariable = toAdd.build();
    if (context.checkVariable(newVariable)) context.updateVariable(newVariable);
    else context.addVariable(newVariable);
    return context;
  }

  public String getToAddValue() {
    return toAdd.build().getValue();
  }

  public boolean toAddExists() {
    Variable add = toAdd.build();
    return add != null
        && (!add.getValue().isEmpty()
        || !add.getType().isEmpty()
        || !add.getName().isEmpty());
  }

  public ContextBuilder addVariable(VariableBuilder toAdd) {
    this.toAdd = toAdd;
    return this;
  }

  public ContextBuilder setToAddName(String name) {
    toAdd.setName(name);
    return this;
  }

  public ContextBuilder setToAddType(VariableType type) {
    toAdd.setType(type);
    return this;
  }

  public ContextBuilder setToAddValue(String value) {
    toAdd.setValue(value);
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
}
