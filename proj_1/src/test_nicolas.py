from goatools.obo_parser import GODag
from goatools.associations import read_gaf
from goatools.base import download_go_basic_obo
from pprint import pprint
import os
import csv

# ----- Lecture .gaf contenant toutes les annotations des protéines humaines, nécessite goa_human.gaf dans le répertoire de travail ------- #
human_annot = read_gaf("goa_human.gaf") 


# Lecture CSV + extraction ensemble protéique
# La version pythonique boucle à l'infini
def proteins_list_from_csv(nom_fichier:str):
    """
    Fonction qui prend en entrée le nom d'un fichier CSV et retourne une liste d'identifiant Uniprot
    Suppose que le fichier d'entrée est en une colonne avec les identifiants Uniprot
    """
    proteins_list = []
    with open(path_to_dataset) as f:
        for line in f:
            proteins_list.append(line.strip())
    
    return proteins_list

# Test
path_to_dataset = "datasets/dataset01.csv"
proteins_list = proteins_list_from_csv(path_to_dataset)

# Dictionnaire GO de nos protéines 
def proteins_to_GO_term(proteins_list:list[str]):
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

the_dict_we_need, the_set_we_need = proteins_to_GO_term(proteins_list)
pprint(the_dict_we_need)
print(the_set_we_need)