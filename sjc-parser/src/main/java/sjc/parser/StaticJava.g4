grammar StaticJava;

@header {
package sjc.parser;
}

compilationUnit
  : ( scds1 += simpleClassDefinition )*
    classDefinition
    ( scds2 += simpleClassDefinition )*
    EOF
  ;

simpleClassDefinition
  : 'class' ID '{'
    publicFieldDeclaration*
    '}'
  ;

publicFieldDeclaration
  : 'public' type ID ';'
  ;

classDefinition
  : 'public' 'class' ID '{'
    mainMethodDeclaration
    memberDeclaration*
    '}'
  ;

memberDeclaration
  : fieldDeclaration | methodDeclaration
  ;

mainMethodDeclaration
  : 'public' 'static' 'void'
    id1=ID                   { "main".equals($id1.text) }?
    '(' id2=ID               { "String".equals($id2.text) }?
    '[' ']' id3=ID ')'
    '{' methodBody '}'
  ;

fieldDeclaration
  : 'static' type ID ';'
  ;

methodDeclaration
  : 'static' returnType ID '(' params? ')' '{' methodBody '}'
  ;

type
  :  ( basicType | ID ) ( t='[' ']' )?
  ;

basicType
  : 'boolean'                #BooleanType
  | 'int'                    #IntType
  ;

returnType
  : 'void'                   #VoidType
  | type                     #NonVoidReturnType
  ;

params
  : param ( ',' param )*
  ;

param
  : type ID
  ;

methodBody
  : localDeclaration* statement*
  ;

localDeclaration
  : type ID ';'
  ;

statement
  : assignStatement
  | ifStatement
  | whileStatement
  | doWhileStatement
  | forStatement
  | incdecStatement
  | invokeExpStatement
  | returnStatement
  ;

assignStatement
  : assign ';'
  ;

assign
  : leftHandSide '=' exp
  ;

leftHandSide
  : ID                       #IdLHS
  | exp '.' ID               #FieldLHS
  | e=exp '[' idx=exp ']'    #ArrayLHS
  ;

ifStatement
  : 'if' '(' exp ')' '{' ts+=statement* '}'
    ( 'else' '{' fs+=statement* '}' )?
  ;

whileStatement
  : 'while' '(' exp ')' '{' statement* '}'
  ;

forStatement
  : 'for' '(' assigns? ';' exp? ';' incdecs? ')'
    '{' statement* '}'
  ;

assigns
  : assign ( ',' assign )*
  ;

incdecs
  : incdec ( ',' incdec )*
  ;

incdec
  : leftHandSide op=( '++' | '--' )
  ;

doWhileStatement
  : 'do' '{' statement* '}' 'while' '(' exp ')' ';'
  ;

incdecStatement
  : incdec ';'
  ;

invokeExpStatement
  : invoke ';'
  ;

returnStatement
  : 'return' exp? ';'
  ;

exp
  : pExp                     #PrimaryExp
  | op=( '-' | '+' ) exp     #UnaryExp
  | op=( '!' | '~' ) exp     #UnaryExp
  | e1=exp
    op=( '*' | '/' | '%' )
    e2=exp                   #BinaryExp
  | e1=exp
    op=( '+' | '-' )
    e2=exp                   #BinaryExp
  | e1=exp
    shiftOp
    e2=exp                   #ShiftBinaryExp
  | e1=exp
    op=( '<' | '>' | '<=' | '>=' )
    e2=exp                   #BinaryExp
  | e1=exp
    op=( '==' | '!=' )
    e2=exp                   #BinaryExp
  | e1=exp op='&' e2=exp     #BinaryExp
  | e1=exp op='^' e2=exp     #BinaryExp
  | e1=exp op='|' e2=exp     #BinaryExp
  | e1=exp op='&&' e2=exp    #BinaryExp
  | e1=exp op='||' e2=exp    #BinaryExp
  | e1=exp '?'
    e2=exp ':' e3=exp        #CondExp
  ;

shiftOp
  : '<' '<'                  #LeftShiftOp
  | '>' '>'                  #RightShiftOp
  | '>' '>' '>'              #URightShiftOp
  ;

pExp
  : INT                      { new java.math.BigInteger($INT.text).bitLength() < 32 }?
                             #IntLiteral
  | 'true'                   #TrueLiteral
  | 'false'                  #FalseLiteral
  | 'null'                   #NullLiteral
  | pPrefix pSuffix*         #PrimaryPrefix
  ;

pSuffix
  : '.' ID                   #AccessSuffix
  | '[' exp ']'              #IndexingSuffix
  ;

pPrefix
  : '(' exp ')'              #ParenExp
  | invoke                   #InvokeExp
  | ID                       #IdExp
  | 'new' ID '[' exp ']'     #NewArrayObject
  | 'new' ID '[' ']'
    '{' args? '}'            #NewArrayObjectInit
  | 'new' ID '(' ')'         #NewObject
  | 'new' basicType
    '[' exp ']'              #NewArrayBasic
  | 'new' basicType '[' ']'
    '{' args? '}'            #NewArrayBasicInit
  ;

invoke
  : ( id1=ID '.' )? id2=ID '(' args? ')'
  ;

args
  : exp ( ',' exp )*
  ;

ID
  : ( 'a'..'z' | 'A'..'Z' | '_' | '$' )
    ( 'a'..'z' | 'A'..'Z' | '_' | '0'..'9' | '$' )*
  ;

INT
  : '0' | ('1'..'9') ('0'..'9')*
  ;

// Whitespace -- ignored
WS
  : [ \r\t\u000C\n]+ -> skip
  ;

// Any other character is an error character
ERROR
  : .
  ;