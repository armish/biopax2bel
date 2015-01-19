package org.mskcc.cbio.bigmech.util;

import org.openbel.bel.model.BELNamespaceDefinition;

public enum BELNamespace {
    HGNC("HGNC", "http://resource.belframework.org/belframework/1.0/namespace/hgnc-approved-symbols.belns", true),
    EGID("EGID", "http://resource.belframework.org/belframework/1.0/namespace/entrez-gene-ids-hmr.belns", false),
    SPAC("SPAC","http://resource.belframework.org/belframework/1.0/namespace/swissprot-accession-numbers.belns", false),
    CHEBI("CHEBI", "http://resource.belframework.org/belframework/1.0/namespace/chebi-names.belns", false),
    GO("GO", "http://resource.belframework.org/belframework/1.0/namespace/go-biological-processes-accession-numbers.belns", false);

    private String prefix;
    private String namespace;
    boolean isDefault;

    public String getPrefix() {
        return prefix;
    }

    public String getNamespace() {
        return namespace;
    }

    public boolean isDefault() {
        return isDefault;
    }

    BELNamespace(String prefix, String namespace, boolean isDefault) {
        this.prefix = prefix;
        this.namespace = namespace;
        this.isDefault = isDefault;
    }

    public BELNamespaceDefinition getBELNamespaceDefinition() {
        return new BELNamespaceDefinition(getPrefix(), getNamespace(), isDefault());
    }
}
