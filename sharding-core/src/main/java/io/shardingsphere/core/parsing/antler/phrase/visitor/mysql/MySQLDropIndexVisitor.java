/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.parsing.antler.phrase.visitor.mysql;

import io.shardingsphere.core.parsing.antler.phrase.visitor.PhraseVisitor;
import io.shardingsphere.core.parsing.antler.util.RuleNameConstants;
import io.shardingsphere.core.parsing.antler.util.TreeUtils;
import io.shardingsphere.core.parsing.antler.util.VisitorUtils;
import io.shardingsphere.core.parsing.parser.sql.SQLStatement;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;

/**
 * Visit MySQL drop index phrase.
 * 
 * @author duhongjun
 */
public final class MySQLDropIndexVisitor implements PhraseVisitor {
    
    @Override
    public void visit(final ParserRuleContext ancestorNode, final SQLStatement statement) {
        List<ParserRuleContext> dropIndexDefs = TreeUtils.getAllDescendantByRuleName(ancestorNode, RuleNameConstants.DROP_INDEX_REF);
        if (null == dropIndexDefs) {
            return;
        }
        for (ParserRuleContext each : dropIndexDefs) {
            int childCnt = each.getChildCount();
            if (0 == childCnt) {
                continue;
            }
            ParseTree lastChild = each.getChild(childCnt - 1);
            if (!(lastChild instanceof ParserRuleContext)) {
                continue;
            }
            ParserRuleContext indexNameNode = (ParserRuleContext) lastChild;
            statement.getSQLTokens().add(VisitorUtils.visitIndex(indexNameNode, statement.getTables().getSingleTableName()));
        }
    }
}
