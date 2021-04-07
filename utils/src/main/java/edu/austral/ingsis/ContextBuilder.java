package edu.austral.ingsis;

public class ContextBuilder {

    private Context context;
    private VariableBuilder toAdd;

    public ContextBuilder(Context context) {
        this.context = context;
    }

    public ContextBuilder() {
        context = new Context();
    }

    public Context build(){
        //TODO arreglar esto
        context.addVariable(toAdd.build());
        return context;
    }

    public String getToAddValue(){
        return toAdd.build().getValue();
    }

    public boolean toAddExists(){
        Variable add = toAdd.build();
        return !add.getValue().isEmpty() && !add.getType().isEmpty() && !add.getName().isEmpty();
    }

    public ContextBuilder addVariable(VariableBuilder toAdd){
        this.toAdd = toAdd;
        return this;
    }

    public ContextBuilder setToAddName(String name){
        toAdd.setName(name);
        return this;
    }

    public ContextBuilder setToAddType(VariableType type){
        toAdd.setType(type);
        return this;
    }

    public ContextBuilder setToAddValue(String value){
        toAdd.setName(value);
        return this;
    }

    public ContextBuilder setNextExecute(Runnable runnable){
        context.setNextExecute(new ExecutingCommand(runnable));
        return this;
    }


}
