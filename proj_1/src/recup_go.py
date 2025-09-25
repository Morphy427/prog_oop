from goatools.associations import read_gaf
from goatools.obo_parser import GODag

# ----- Lecture .gaf contenant toutes les annotations des protéines humaines, nécessite goa_human.gaf dans le répertoire de travail ------- #
human_annot = read_gaf("goa_human.gaf") 
obodag = GODag("go-basic.obo") # Voir pour gérer term.is_obsolete 

# Lecture CSV + extraction ensemble protéique
def proteins_list_from_csv(nom_fichier:str):
    """
    Fonction qui prend en entrée le nom d'un fichier CSV et retourne une liste d'identifiant Uniprot
    Suppose que le fichier d'entrée est en une colonne avec les identifiants Uniprot
    """
    with open(path_to_dataset) as f:
        proteins_list = [line.strip() for line in f]
        f.close()

    return proteins_list

# Dictionnaire GO de nos protéines 
def proteins_id_to_GO_term(proteins_list:list[str]):
    """Fonction qui prend en entréée une liste d'identifiant Uniprot et renvoie un dictionnaire protéine:goterm ainsi qu'un set contenant tous les go term"""
    final_dict = dict()
    all_go_term = set()
    for protein_id in proteins_list:
        temp_dict = {protein_id:[]}
        for elt in human_annot[protein_id]:
            temp_dict[protein_id].append(elt)
            all_go_term.add(elt)
        final_dict.update(temp_dict)
    return(final_dict,all_go_term)

def reverse_dict(dictionnary:dict[str:list[str]]):
    """Fonction qui inverse les clés / valeurs du dictionnaire de la fonction proteins_id_to_GO_term()"""
    new_dict = dict()
    for uniprot_id, go_list in the_dict_we_need.items():
        for go in go_list:
            if go in new_dict.keys():
                new_dict[go].append(uniprot_id)
            else:
                new_dict.update({go:[uniprot_id]})

    return(new_dict)

def dict_for_ascendant(path_to_BP_hierarchy:str):
    """Fonction qui lit geneOntology-BP-hierarchy-indirect.tsv pour le convertir en dictionnaire descendant:ascendant"""
    future_dict = dict()
    with open(path_to_BP_hierarchy,"r") as f:
        temp_list = [line.strip().split("\t") for line in f]
        for asc,desc in temp_list:
            future_dict.update({asc:desc})
    return future_dict
