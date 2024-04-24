import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Parser {

    private final TokenManager manageTkn;//private member
    private final StatementsNode allStates = new StatementsNode();


    public Parser(LinkedList<Token> listIn) {
        this.manageTkn = new TokenManager(listIn);//constructor
    }

    //we will go through all the tokens to check to see if separator token ENDOFLINE is found in the token
    boolean AcceptSeparators() {
        int checkAll = 0;
        boolean areThereSeperators = false;
        Token.TokenType isEndOfLine;
        //any number will result in true
        while(!manageTkn.Peek(checkAll).equals(Optional.empty())) {
            isEndOfLine = manageTkn.Peek(checkAll).get().tokenKind;
            //gets Token, then we get the TokenType and check if it is equal to endofline
            if(isEndOfLine.equals(Token.TokenType.ENDOFLINE)) areThereSeperators = true;
            checkAll++;
        }
        return areThereSeperators;

    }

    public ProgramNode parse() {
        boolean anySeperators = AcceptSeparators();
        LinkedList<Node> mathList = new LinkedList<>();

        //loop until list empty
        while(manageTkn.MoreTokens()) {
            //MAKE A SWITCH/IFELSE for determining what type of node to build
            mathList.add(Expression());
            Statements();

        }

        return new ProgramNode(mathList);
    }

    //nothing needs to be returned as the math equations are being added to the AST
    //This will deal with Adding and Subtraction since it's the last things to do in PEMDAS
    //We will add to ProgramNode bottom up so The first thing we check is the last to add.
    public Node Expression () {
        Node term;
        term = Term();//sends the first value through
        Optional<Token> toCheck = manageTkn.Peek(0);//look at the current token
        manageTkn.MatchAndRemove(toCheck.get().tokenKind);
        //for event of multiple addition/subtraction on one line
        while((toCheck.get().tokenKind.equals((Token.TokenType.ADD)) || toCheck.get().tokenKind.equals(Token.TokenType.SUBTRACT))) {
            if (toCheck.get().tokenKind.equals(Token.TokenType.ADD)) {
                manageTkn.MatchAndRemove(Token.TokenType.ADD);//get rid of negative subtract symbol
                term = new MathOpNode("+", term, Term());
            }else if (toCheck.get().tokenKind.equals(Token.TokenType.SUBTRACT)) {
                manageTkn.MatchAndRemove(Token.TokenType.SUBTRACT);//get rid of negative subtract symbol
                term = new MathOpNode("-", term, Term());
            }
            toCheck = manageTkn.Peek(0);
        }
        return term;
    }

    //will handle operators multiply and divide
    public Node Term () {
        Node factor;
        factor = Factor();
        Optional<Token> toCheck = manageTkn.Peek(0);//look at the first token in list
        //for event of multiple multiplication/subtraction on one line
        while(toCheck.get().tokenKind.equals((Token.TokenType.MULTIPLY)) || toCheck.get().tokenKind.equals(Token.TokenType.DIVIDE)) {
            manageTkn.MatchAndRemove(toCheck.get().tokenKind);
            if (toCheck.get().tokenKind.equals(Token.TokenType.MULTIPLY)) {
                manageTkn.MatchAndRemove(Token.TokenType.MULTIPLY);//get rid of negative subtract symbol
                new MathOpNode("*", factor, Factor());
            }else if (toCheck.get().tokenKind.equals(Token.TokenType.DIVIDE)) {
                manageTkn.MatchAndRemove(Token.TokenType.DIVIDE);//get rid of negative subtract symbol
                new MathOpNode("/", factor, Factor());
            }
            toCheck = manageTkn.Peek(0);
        }

        return factor;
    }

    //will return a float or integer node or data NOTE unary minus (negative numbers is handled here too)
    public Node Factor () {
        Optional<Token> toCheck = manageTkn.Peek(0);//look at the first token in list
        //need to handle negative numbers
        if (toCheck.get().tokenKind.equals(Token.TokenType.NUMBER)) {
            toCheck = manageTkn.MatchAndRemove(Token.TokenType.NUMBER);//get rid of negative subtract symbol
            return numberType(toCheck.get().tokenData);//get the number and type FLOAT/INTEGER by helper method
        }else if (toCheck.get().tokenKind.equals(Token.TokenType.WORD)) {//Add variables to Factor
            toCheck = manageTkn.MatchAndRemove(Token.TokenType.WORD);
            return new VariableNode(toCheck);
        }else if (toCheck.get().tokenKind.equals(Token.TokenType.LPAREN)) {//Expression found in parentheses
            return Expression();
        }else {
            System.out.println(toCheck);
            return null;
        }
    }

    //helper operand to decide if number is float or int
    public Node numberType (String numIn) {
        //check if the value is not contained within an Int number
        if(numIn.contains(".")) {
            return new FloatNode(Float.valueOf(numIn));
        }else {
            return new IntegerNode(Integer.valueOf(numIn));
        }
    }

    //PARSER 2
    //statements should accept any number of statments
    public void Statements() {
        //once statement returns null
        Node buildStatment = Statement();
        while(buildStatment != null) {
            ParseTokens();//AST then add to Node, Adds token to a bst
            allStates.getStatementNodeList(buildStatment);
            buildStatment = Statement();
        }
    }

    //handles 1 statement
    public StatementNode Statement() {
        //handle a PrintStatement OR Assignment
        Token.TokenType holder = manageTkn.Peek(0).get().tokenKind;
        StatementNode toFill;
        switch (holder) {
            case PRINT:
                manageTkn.MatchAndRemove(Token.TokenType.PRINT);
                //call printNode statement
                toFill = new PrintNode();
                return toFill;
            case GOSUB:
                //requires an identifier
            case RETURN:
                //alone
                //return new LabledStatementNode();
            case FOR:
                //step and increment on for is optional, so if it does not have a step increment is 1
            case NEXT:
            case STEP:
            case END:
                //no param just makes an end Label Node
            case IF:
                //get expression
                //get operator >, >=, <, <=, <>, =
                //get expression
            case WHILE:
                //boolean just like if statements
                //or function invocation
                functionInvocation();
            case LABEL:
            default:
                return null;
        }
    }

    FunctionNode functionInvocation() {
        Token functionName = manageTkn.Peek(0).get();
        List<Token> paramList = new ArrayList<>();
        //requires the param list to have 1 or more expressions or Strings
        if(paramList.size() >= 1) {
           return new FunctionNode(functionName, paramList);
           //function follows this patter functionName(String), LPAREN, paramList, RPARAM
        }else {
            return null;
        }

    }

    //if a print variable is found the next expression should be printed
    public PrintNode PrintStatement() {
        //if not empty the statement returns a Node
        Optional<Token> toPrint = manageTkn.Peek(0);
        Node toAdd;
        PrintNode builtPrint = new PrintNode();
        if(toPrint != null) {
            manageTkn.MatchAndRemove(toPrint.get().tokenKind);
            toAdd = new VariableNode(toPrint);
            builtPrint.AddtoPrintNode(toAdd);
            return builtPrint;
        }else {//no statement can be made so null is removed
            return null;
        }
    }

    //Prints out a list that is seperated by comma
    //Should also accept String Node
    public void PrintList (List<Node> listToPrint) {
        Token comma = new Token(Token.TokenType.COMMA, 0, 0);
        Node seperatorNode = new VariableNode(Optional.of(comma));
        for(Node printing: listToPrint) {
            System.out.print(listToPrint);
        }
    }

    public AssignmentNode Assignment(Token assign) {
        if(assign.tokenKind.equals(Token.TokenType.EQUALS)) {
            return null;
            //return new AssignmentNode();
        }else if(assign.tokenKind.equals(Token.TokenType.LESSTHAN) || assign.tokenKind.equals(Token.TokenType.LESSTHANEQUALS)) {
            return null;
            //return new AssignmentNode();
        }else if(assign.tokenKind.equals(Token.TokenType.GREATERTHAN) || assign.tokenKind.equals(Token.TokenType.GREATERTHANEQUALS)) {
            return null;
            //return new AssignmentNode();
        }else {
            return null;
        }
    }

    public void ParseTokens() {
        new ASTNode(manageTkn.Peek(1), manageTkn.Peek(0), manageTkn.Peek(2));
    }

}
