import java.util.*;

//We pull all the data as a LinkedList
public class Interpretor {
    //interpretor constructor
    boolean setTestingMode = true;//testing mode for running print and input in test mode
    ArrayList<Node> labelsToAssign = StatementsNode.getList();

    public Interpretor (ArrayList<Node> interpretFromParse, LinkedList<ReadNode> reading) {

        //get the AST from parser, and use a visitor walk pattern
        HashMap<String, LabledStatementNode> stringToLabledStatement = new HashMap<>();//Search for
        //for the 3 data types Integer, Float and String
        HashMap<String, Integer> stringToInt = new HashMap<>();
        HashMap<String, Float> stringToFloat = new HashMap();
        HashMap<String, String> stringToString = new HashMap();

        //we search for labled statements node and add to LinkedList

        //confused on why this is being an issue if LabeledStatementNode extends Node
        for (Node checkLabel : labelsToAssign) {
            if (checkLabel instanceof LabledStatementNode) {
                //for somereason casting as labeledStatementNode causes error
                //stringToLabledStatement(((LabledStatementNode) checkLabel).getLabel(), ((LabledStatementNode) checkLabel));
            }
        }

        for (Node toAnalyze : reading) {
            if (toAnalyze instanceof StringNode) {
                stringToString.put(toAnalyze.toString(), ((StringNode) toAnalyze).getString());
            } else if (toAnalyze instanceof IntegerNode) {
                stringToInt.put(toAnalyze.toString(), ((IntegerNode) toAnalyze).getInteger());
            } else if (toAnalyze instanceof FloatNode) {
                stringToFloat.put(toAnalyze.toString(), ((FloatNode) toAnalyze).getNumberFloat());
            } else {
                throw new RuntimeException("A data type is unreconizable");
            }
        }

        runthrough(stringToInt, stringToFloat, stringToString, stringToLabledStatement);
    }

    private void runthrough(HashMap<String, Integer> num, HashMap<String, Float> flt, HashMap<String, String> str, HashMap<String,LabledStatementNode> strLabel) {
        Iterator<Map.Entry<String, LabledStatementNode>> iterator = strLabel.entrySet().iterator();
        //iterate through loop of statements and data at same time then bind them depending on type of Label
        /*for(iterator.hasNext()) {
            Map<String, LabledStatementNode> in = (Map<String, LabledStatementNode>) iterator.next();
            String key = in.getKey();
            LabledStatementNode value = in.getValue();
        }*/
    }

    //Implement the built-in functions here(LEFT$, RIGHT$, MID$, NUM$, VAL, VAL% and RANDOM()
    static String left() {
        return null;
    }
    static String right() {
        return null;
    }
    static String mid() {
        return null;
    }
    static String num() {
        //bind Int/Float to the appropriate LabeledStatement
        return null;
    }
    //Assign a value to a
    static String val() {
        //bind String to the appropriate LabeledStatement
        return null;
    }
    //Process percentage equation here
    static String valPercent() {
        return null;
    }
    //Assign the labeledNode to a random integer
    static float randomV() {
        Random makeRand = new Random();
        int randNum = makeRand.nextInt(); // number between 0 and 100
        return Integer.parseInt(String.valueOf(randNum));
    }

    //Part 7
   Float evaluateFloat(FloatNode type) {
        return type.getNumberFloat();
   }

   int evaluateInt(IntegerNode type) {
        return type.getInteger();
   }

   void Evaluate() {
        for(Node type : labelsToAssign) {
            if (type instanceof VariableNode) {
                lookUpVariable((VariableNode) type);
            }else if (type instanceof IntegerNode) {
                evaluateInt(((IntegerNode) type));
            }else if (type instanceof FloatNode) {
                Float v = evaluateFloat(((FloatNode) type));
            }else if (type instanceof MathOpNode) {//gets mathOperation
                Node left = ((MathOpNode) type).getLeft();
                MathOpNode.mathType operator = ((MathOpNode) type).getOp();
                Node right = ((MathOpNode) type).getRight();
            }else {//handles function calls
                Evaluate();
            }
        }
   }

    void lookUpVariable(VariableNode type) {
        //checks what the variable type is
        VariableNode.typeOfVariable getTypeVar;
    }

    void Interpret(StatementNode toProcess){
        if(toProcess instanceof ReadNode){
            readProcess((ReadNode) toProcess);
        }else if(toProcess instanceof AssignmentNode) {
            runAssignment((AssignmentNode) toProcess);
        }else if (toProcess instanceof InputNode) {
            Node inputFound = findInput((InputNode) toProcess);
        }else if(toProcess instanceof PrintNode) {
            Node printFound = printThePrintNode((PrintNode) toProcess);
        }
   }

    void runAssignment(AssignmentNode toProcess) {


    }

    //Handles ReadNode
   InterpretorDataType readProcess(ReadNode reading) {
        List<Node> in = reading.getReadNode();
        for(int i = 0; i <= in.size(); i++) {


        }
       return null;
   }

   //handles the mode and evaluation of the printNode
   Node printThePrintNode(PrintNode toPrint){
       if (setTestingMode) {//evaluate known string
           System.out.println(toPrint);
       } else if (!setTestingMode) {
          //
       }

       return null;
   }
   //Get input from user
   AssignmentNode findInput(InputNode toInput) {
       List<String> holdInput = null;
       if (setTestingMode) {
           holdInput = toInput.getPreDefined();
           //print all strings
           for(String printing : holdInput) {
               System.out.println(printing);
           }
           //Add this to the relative hash
           return new AssignmentNode(VariableNode.typeOfVariable.STRING , new InputNode(holdInput));
        }else if(!setTestingMode){
            //gets the input of the user when not in testing mode
            Scanner kb = new Scanner(System.in);
            String toAdd= kb.next();
            holdInput.add(toAdd);
            //prints the string inputted and assigns the value to the inputNode
            System.out.println(toAdd);
            //Add this to the relative hash
            return new AssignmentNode(VariableNode.typeOfVariable.STRING , new InputNode(holdInput));
       }
       return null;
   }

    public void interpretFunction(FunctionNode node, HashMap<String, InterpretorDataType> map) {
        ArrayList<VariableNode> params = node.getParameterList();
        ArrayList<VariableNode> variables = node.getVariables();
        ArrayList<StatementNode> statements = node.getStatements();

        if(params != null){
            ArrayList<VariableNode> arguments = new ArrayList<>();
            for (VariableNode param : params) {
                VariableNode varData = param;
                arguments.add(varData);
            }
        }
    }



}
