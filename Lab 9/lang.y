%{
#include <stdio.h>
#include <stdlib.h>

#define YYDEBUG 1
%}

%token programa
%token finprograma
%token listo
%token leer
%token variable
%token entero
%token doble
%token booleano
%token chaar
%token fi
%token filse
%token lse
%token fiend
%token entonces
%token iniciobucle
%token finbucle
%token desde
%token hasta
%token imprime
%token devolver
%token romper
%token and
%token or
%token IDENTIFIER
%token CONSTANT
%token SEMI_COLON
%token COMA
%token DOT
%token PLUS
%token MINUS
%token MULTIPLY
%token DIVISION
%token MODULO
%token LEFT_ROUND_BRACKETS
%token RIGHT_ROUND_BRACKETS
%token LEFT_SQUARE_BRACKETS
%token RIGHT_SQUARE_BRACKETS
%token LEFT_CURLY_BRACKETS
%token RIGHT_CURLY_BRACKETS
%token QUESTION_MARK
%token LESS_THAN
%token GREATER_THAN
%token LESS_OR_EQUAL_THAN
%token GREATER_OR_EQUAL_THAN
%token DIFFERENT
%token EQUAL
%token ASSIGNMENT
%token AND_OPERATOR
%token OR_OPERATOR

%start program

%%

program : programa decllist compoundStatement finprograma ;
decllist : declaration SEMI_COLON decllist | declaration ;
declaration : variable type IDENTIFIER ;
type : primitiveType ;
primitiveType : booleano | chaar | entero | doble ;
compoundStatement : LEFT_CURLY_BRACKETS statementList RIGHT_CURLY_BRACKETS ;
statementList : statement SEMI_COLON statementList | statement ;
statement : simpleStatement | structStatement ;
simpleStatement : assignmentStatement | ioStatement ;
assignmentStatement : IDENTIFIER ASSIGNMENT expression ;
expression : term | term PLUS expression | term MINUS expression | term MULTIPLY expression | term DIVISION expression | term MODULO expression | LEFT_ROUND_BRACKETS expression RIGHT_SQUARE_BRACKETS ;
term : IDENTIFIER | CONSTANT ;
ioStatement : leer type IDENTIFIER | imprime IDENTIFIER ;
structStatement : ifStatement | forStatement ;
ifStatement : fi LEFT_ROUND_BRACKETS condition RIGHT_ROUND_BRACKETS entonces statementList fiend | filse LEFT_ROUND_BRACKETS condition RIGHT_ROUND_BRACKETS entonces statementList fiend ;
forStatement : iniciobucle IDENTIFIER desde IDENTIFIER hasta IDENTIFIER statementList finbucle ;
condition : expression relation expression ;
relation : LESS_THAN | GREATER_THAN | LESS_OR_EQUAL_THAN | GREATER_OR_EQUAL_THAN | DIFFERENT | EQUAL ;

%%

yyerror(char *s)
{
  printf("%s\n", s);
}

extern FILE *yyin;