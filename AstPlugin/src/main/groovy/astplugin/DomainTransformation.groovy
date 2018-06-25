package astplugin

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.tools.GeneralUtils
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*

import java.lang.reflect.Modifier

class DomainTransformation extends AbstractASTTransformation {

    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        ClassNode classNode = sourceUnit.getAST().getClasses().stream().findAny().get()
        MakeGetByIdMethod(classNode)
    }

    static private MethodNode MakeGetByIdMethod(ClassNode classNode) {

        Parameter idParameter = new Parameter(
                ClassHelper.make(Long.class),
                "id"
        )

        VariableExpression idVariableExpression = new VariableExpression(idParameter)
        VariableExpression recordVariableExpression = new VariableExpression("record")

        BlockStatement methodBlockStatement = new BlockStatement()
        methodBlockStatement.addStatement(RecordReadStatement(classNode, recordVariableExpression, idVariableExpression))
        methodBlockStatement.addStatement(IfNotRecordStatement(classNode, recordVariableExpression, idVariableExpression))
        methodBlockStatement.addStatement(new ExpressionStatement(recordVariableExpression))
        def parameters = [] < idParameter

        return classNode.addMethod(
                "getById",
                Modifier.STATIC,
                ClassHelper.OBJECT_TYPE,
                parameters,
                new ClassNode[0],
                methodBlockStatement
        )
    }

    static private Statement RecordReadStatement(ClassNode domainClassNode, VariableExpression recordVariableExpression, Expression idExpression) {

        MethodCallExpression domainRead = new MethodCallExpression(
                new ClassExpression(domainClassNode),
                "read",
                idExpression
        )

        DeclarationExpression recordDeclaration = new DeclarationExpression(
                recordVariableExpression,
                GeneralUtils.ASSIGN,
                domainRead)

        return new ExpressionStatement(recordDeclaration)
    }
    static private IfStatement IfNotRecordStatement(ClassNode domainClassNode, Expression recordExpression, Expression idExpression) {

        BooleanExpression notRecord = new BooleanExpression(
                new NotExpression(recordExpression)
        )

        BlockStatement throwException = new BlockStatement()
        throwException.addStatement(ThrowRecordNotFoundStatement(domainClassNode, idExpression))

        return new IfStatement(
                notRecord,
                throwException,
                new BlockStatement()
        )
    }

    static private ThrowStatement ThrowRecordNotFoundStatement(ClassNode domainClassNode, Expression idExpression) {

        ArgumentListExpression exceptionArguments = new ArgumentListExpression(
                new ConstantExpression(domainClassNode.getNameWithoutPackage()),
                new ConstantExpression("read"),
                idExpression
        )

        ConstructorCallExpression newRecordNotFoundException = new ConstructorCallExpression(
                ClassHelper.make("xast.exception.RecordNotFoundException"),
                exceptionArguments
        )

        return new ThrowStatement(newRecordNotFoundException)
    }
}

