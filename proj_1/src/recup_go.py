from goatools.associations import read_gaf

# ----- Lecture .gaf contenant toutes les annotations des protéines humaines, nécessite goa_human.gaf dans le répertoire de travail ------- #
human_annot = read_gaf("goa_human.gaf") 

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
