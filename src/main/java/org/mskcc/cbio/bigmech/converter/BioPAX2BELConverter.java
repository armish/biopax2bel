package org.mskcc.cbio.bigmech.converter;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level3.BiochemicalReaction;
import org.biopax.paxtools.pattern.miner.SIFEnum;
import org.biopax.paxtools.pattern.miner.SIFInteraction;
import org.biopax.paxtools.pattern.miner.SIFSearcher;
import org.mskcc.cbio.bigmech.util.BELHeaderWrapper;
import org.mskcc.cbio.bigmech.util.BELNamespace;
import org.mskcc.cbio.bigmech.util.BELUtils;
import org.openbel.bel.model.*;
import org.openbel.framework.core.compiler.SymbolWarning;

import java.util.*;


public class BioPAX2BELConverter {
    private BELUtils belUtils = new BELUtils();
    private BELHeaderWrapper belHeaderWrapper = new BELHeaderWrapper(
            "Document #" + UUID.randomUUID().toString(),
            "Converted from BioPAX on " + (new Date()).toString(),
            "1.0"
    );

    public BELHeaderWrapper getBelHeaderWrapper() {
        return belHeaderWrapper;
    }

    public void setBelHeaderWrapper(BELHeaderWrapper belHeaderWrapper) {
        this.belHeaderWrapper = belHeaderWrapper;
    }

    public BELUtils getBelUtils() {
        return belUtils;
    }

    public void setBelUtils(BELUtils belUtils) {
        this.belUtils = belUtils;
    }

    public BELDocument convert(Model bpModel) {
        BELDocument belDocument = getBelUtils().createNewBELDocument(belHeaderWrapper);

        // TODO: Create groups for pathways (make sure you handle multiple layers of pathways)
        // Create a common group
        List<BELStatementGroup> statementGroups = belDocument.getBelStatementGroups();
        BELStatementGroup statementGroup = new BELStatementGroup("BioPAX Reactions");
        statementGroups.add(statementGroup);

        // Let's convert the model into SIF
        SIFSearcher searcher = new SIFSearcher(
                SIFEnum.CONTROLS_STATE_CHANGE_OF,
                SIFEnum.CONTROLS_EXPRESSION_OF,
                SIFEnum.CONTROLS_PRODUCTION_OF,
                SIFEnum.CONTROLS_PHOSPHORYLATION_OF,
                SIFEnum.CONTROLS_TRANSPORT_OF
        );

        Set<SIFInteraction> sifInteractions = searcher.searchSIF(bpModel);
        for (SIFInteraction sifInteraction : sifInteractions) {
            // and convert each interaction into a BEL Statement
            statementGroup.getStatements().add(convertSIF2BelStatement(sifInteraction));
        }

        return belDocument;
    }

    private BELStatement convertSIF2BelStatement(SIFInteraction sifInteraction) {
        StringBuilder statement = new StringBuilder();
        assert sifInteraction.type instanceof SIFEnum;
        SIFEnum sifType = (SIFEnum) sifInteraction.type;
        String src = belUtils.namespaceId(sifInteraction.sourceID, BELNamespace.HGNC);
        String target = belUtils.namespaceId(sifInteraction.targetID, BELNamespace.HGNC);

        // TODO: Implement these -- right now, they are mostly dummy
        switch(sifType) {
            case CONTROLS_STATE_CHANGE_OF:
                statement.append("p(HGNC:BLA) -> p(HGNC:FOO)");
                break;
            case CONTROLS_EXPRESSION_OF:
                statement.append("p(HGNC:BLA) -> p(HGNC:FOO)");
                break;
            case CONTROLS_PRODUCTION_OF:
                statement
                        .append("p(").append(src).append(")")
                        .append(" -> ")
                        .append("p(").append(target).append(")");
                break;
            case CONTROLS_PHOSPHORYLATION_OF:
                statement.append("p(HGNC:BLA) -> p(HGNC:FOO)");
                break;
            case CONTROLS_TRANSPORT_OF:
                statement.append("p(HGNC:BLA) -> p(HGNC:FOO)");
                break;
            default:
                throw new NotImplementedException();
        }

        /*
        BELCitation belCitation = new BELCitation(
                "Publication",
                "PMID",
                StringUtils.join(sifInteraction.getPubmedIDs(), ",")
        );
        BELEvidence belEvidence = new BELEvidence(sifInteraction.getMediatorsInString());
        return new BELStatement(
                statement.toString(),
                new ArrayList<BELAnnotation>(),
                belCitation,
                belEvidence,
                sifInteraction.toString()
        );
        */

        return new BELStatement(statement.toString());
    }
}
