package org.mskcc.cbio.bigmech.converter;

import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level3.BiochemicalReaction;
import org.mskcc.cbio.bigmech.util.BELHeaderWrapper;
import org.mskcc.cbio.bigmech.util.BELUtils;
import org.openbel.bel.model.BELDocument;

import java.util.UUID;


public class BioPAX2BELConverter {
    private BELUtils belUtils = new BELUtils();
    private BELHeaderWrapper belHeaderWrapper = new BELHeaderWrapper(UUID.randomUUID().toString());

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
        for (BiochemicalReaction reaction : bpModel.getObjects(BiochemicalReaction.class)) {
            // TODO: iterate over all reactions and conver them to BelStatement(s)
        }
        return belDocument;
    }
}
