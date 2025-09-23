from goatools.obo_parser import GODag
from goatools.anno.genetogo_reader import Gene2GoReader

# Charger l'ontologie GO
godag = GODag("go-basic.obo")

# Charger les annotations (ex: gene2go)
objanno = Gene2GoReader("gene2go", godag=godag)
