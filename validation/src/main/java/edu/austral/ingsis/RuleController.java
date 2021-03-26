package edu.austral.ingsis;

import java.util.List;
import java.util.stream.IntStream;

public class RuleController {

    //TODO index management

    public static Variable declarationCommand(List<Token> tokens, Context context){
        return new VariableBuilder()
                .setName(tokens.get(1).getValue())
                .setType(tokens.get(3).getValue(), tokens.get(3).getType().getOrdinal())
                .build();
    }

    public static Variable assignationCommand(List<Token> tokens, Context context){
        int index = IntStream.range(0, tokens.size())
                .filter(t -> tokens.get(t).getType().equals(Operator.EQUAL))
                .toArray()[0];
        Variable assigned = operationCommand(tokens.subList(index+1, tokens.size()), context);
        if(tokens.get(0).getType().equals(KeyWord.DECLARATION)){
            return new VariableBuilder()
                    .setName(tokens.get(1).getValue())
                    .setType(assigned.getType())
                    .build();
        } else {
            return new VariableBuilder()
                    .setName(tokens.get(0).getValue())
                    .setType(assigned.getType())
                    .build();
        }
    }

    public static Variable operationCommand(List<Token> tokens, Context context){
        if(tokens.stream().anyMatch(t -> t.getType().getCategory().equals(Operator.PLUS.getCategory()))){ //OPERATOR
            int index = IntStream.range(0, tokens.size())
                    .filter(t -> tokens.get(t).getType().getCategory().equals(Operator.PLUS.getCategory()))
                    .toArray()[0];
            List<Token> before = tokens.subList(0, index);
            List<Token> after = tokens.subList(index+1, tokens.size());
            Variable beforeResult = operationCommand(before, context);
            Variable afterResult = operationCommand(after, context);
            VariableType type = beforeResult.getType().getOrdinal() < afterResult.getType().getOrdinal() ? beforeResult.getType() : afterResult.getType();
            return new VariableBuilder().setType(type).build();
        } else {
            if(tokens.get(0).getType().getCategory().equals(KeyWord.STRING.getCategory())){ //VALUE
                return new VariableBuilder().setType(tokens.get(0).getType().getName(), tokens.get(0).getType().getOrdinal()).build();
            } else {
                Variable aux = new VariableBuilder().setName(tokens.get(0).getValue()).build();
                if(!context.checkVariable(aux)) throw new RuntimeException("Non declared variable! in: " + tokens.get(0).getPosition().toString());
                return new VariableBuilder().setName(aux.getName()).setType(context.getVariableType(aux.getName())).build();
            }
        }
    }

    public static Variable printCommand(List<Token> tokens, Context context){
        if(tokens.get(2).getType().getCategory().equals(KeyWord.STRING.getCategory())){ //VALUE
            return new VariableBuilder().setType(tokens.get(2).getType().getName(), tokens.get(2).getType().getOrdinal()).build();
        } else {
            Variable aux = new VariableBuilder().setName(tokens.get(2).getValue()).build();
            if(!context.checkVariable(aux)) throw new RuntimeException("Non declared variable! in: " + tokens.get(2).getPosition().toString());
            return new VariableBuilder().setName(aux.getName()).setType(context.getVariableType(aux.getName())).build();
        }
    }


}
