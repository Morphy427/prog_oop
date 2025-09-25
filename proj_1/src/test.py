from goatools.obo_parser import GODag
from goatools.anno.genetogo_reader import Gene2GoReader
from goatools.base import download_go_basic_obo
download_go_basic_obo()
# Charger l'ontologie GO
godag = GODag("go-basic.obo")

# Charger les annotations (ex: gene2go)
# objanno = Gene2GoReader("gene2go", godag=godag)

# ----- Partie nicolas, temporaire pour changer d'environnement vu que mac ne me permet pas d'écrire sur mon disque dur è.é
# from goatools.obo_parser import GODag
from goatools.associations import read_gaf
# from goatools.base import download_go_basic_obo
# from pprint import pprint
# import os

# Utilité ?
# test = download_go_basic_obo() # install go-basic.obo
# godag = GODag("go-basic.obo")

# Lecture .gaf contenant toutes les annotations des protéines humaines, nécessite goa_human.gaf dans le répertoire de travail
# human_annot = read_gaf("goa_human.gaf") 

# Dictionnaire en sortie : key = ID uniprot, valeur = GO term. 

protein_id = "P06132"  # HBA1 (hémoglobine sous-unité alpha)

# if protein_id in human_annot:
#     print("GO terms pour", protein_id, ":", human_annot[protein_id])

obodag = GODag("go-basic.obo")
print(obodag.paths_to_top("GO:0042158"))
# term = obodag["GO:0000086"]   # G2/M transition of mitotic cell cycle
# print(term.get_all_parents())
# print(term.name)              # Nom du terme
# print(term.namespace)

