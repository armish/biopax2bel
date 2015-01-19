package org.mskcc.cbio.bigmech.util;

import org.openbel.bel.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BELUtils {
    public BELDocument createNewBELDocument(BELHeaderWrapper belHeaderWrapper) {
        // Add all namespace definitions relevant to BioPAX documents
        final Set<BELNamespaceDefinition> nsset = new HashSet<BELNamespaceDefinition>();
        for (BELNamespace belNamespace : BELNamespace.values()) {
            nsset.add(belNamespace.getBELNamespaceDefinition());
        }

        // Create empty statement lists and annotations
        final List<BELStatementGroup> belStatementGroups = new ArrayList<BELStatementGroup>();
        final Set<BELAnnotationDefinition> annotationDefinitions = new HashSet<BELAnnotationDefinition>();

        // Return a new bel document
        return new BELDocument(
                belHeaderWrapper.getAsBELDocumentHeader(),
                annotationDefinitions,
                nsset,
                belStatementGroups
        );
    }

}
