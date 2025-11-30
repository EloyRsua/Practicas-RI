import bm25s
import Stemmer

def load_qrels(file):
    """
    Carga la lista de los documentos relevantes
    El documento tiene el siguiente formato query_id 0 doc_id relevance
    """
    qrels = []
    with open(file , "r",encoding="UTF-8") as f:
        for line in f:
            parts = line.strip().split()
            qrels.append([parts[0], parts[2], parts[3]]) 

    return qrels

def load_questions (file):
    """
    Cargan todas las preguntas del fichero
    El documento tiene el siguiente formato query_id url question
    """
    questions = []
    with open(file , "r",encoding="UTF-8") as f:
        for line in f:
            parts = line.strip().split("\t")
            questions.append([parts[0], parts[1], parts[2]]) 

    return questions

def evaluate_bm25(questions):
   
    retriever = bm25s.BM25.load("pubmed", load_corpus=True)
    stemmer = Stemmer.Stemmer("english")

    all_results = []  

    for query_entry in questions:
        query_text = query_entry[2].strip()  
        query_id = query_entry[0]           

        if not query_text:
            continue

        # Tokenizar la pregunta
        query_tokenized = bm25s.tokenize(query_text, stemmer=stemmer, stopwords="en", lower=True ,show_progress=False)

        # Recuperar resultados
        results, scores = retriever.retrieve(query_tokenized, k=100, corpus=retriever.corpus,show_progress=False)

        # Guardar los doc_ids en la lista
        for rank in range(results.shape[1]):
            result = results[0, rank]
            all_results.append((query_id, result['id']))

    return all_results

def count_relevant_per_query_list(bm25_results, qrels):
    """
    bm25_results: lista de tuplas (query_id, doc_id)
    qrels: lista de listas [query_id, doc_id, relevance]
    
    Devuelve: diccionario query_id -> número de documentos relevantes recuperados
    """
    counts = {}

    for query_id, doc_id in bm25_results:
        if query_id not in counts:
            counts[query_id] = 0

        # Revisar cada qrel para ver si coincide con la query y doc actual
        for qid, rel_doc_id, rel in qrels:
            if qid == query_id and rel_doc_id == doc_id:
                counts[query_id] += 1
                break  # Si ya lo encontramos, no hace falta seguir buscando

    return counts

def calc_precission(relevant_counts):
    precission = {}
    for query_id,count in relevant_counts.items():
        precission[query_id]=int(count)/100
    return precission

def calc_exahustividad(relevant_counts, qrels):
    """
    relevant_counts: dict { query_id: count_relevantes_recuperados }
    qrels: lista de listas [query_id, doc_id, relevance]

    Devuelve: dict { query_id: recall }
    """
    # 1) Contar cuántos documentos relevantes existen por query (total_relevantes)
    total_relevantes = {}
    for qid, doc_id, rel in qrels:
        if qid not in total_relevantes:
            total_relevantes[qid] = 0
        total_relevantes[qid] += 1

    # 2) Calcular recall por query usando los contadores ya calculados
    recalls = {}
    for query_id, count in relevant_counts.items():
        total = total_relevantes.get(query_id, 0)
        if total == 0:
            recalls[query_id] = 0.0         
        else:
            recalls[query_id] = int(count) / total

    return recalls

def calc_f1(precission, recall):
    f1_scores = {}

    for query_id in precission:
        p = precission.get(query_id, 0)
        r = recall.get(query_id, 0)

        if p + r == 0:
            f1_scores[query_id] = 0.0
        else:
            f1_scores[query_id] = 2 * p * r / (p + r)

    return f1_scores

def calc_media(metric_dict):
    total = 0
    count = 0

    for query_id, value in metric_dict.items():
        total += value
        count += 1

    if count == 0:
        return 0.0

    return total / count

def seleccionar_extremos(metric_dict, n=5):
    """
    Devuelve las n consultas mejores y las n peores según la métrica dada.
    metric_dict: diccionario {query_id: valor_metrica}
    """ 
    ordenadas = sorted(metric_dict.items(), key=lambda x: x[1])

    peores = ordenadas[:n]        # n con menor rendimiento
    mejores = ordenadas[-n:]      # n con mayor rendimiento

    return peores, mejores

questions=load_questions("NFcorpus-questions-selection.txt")
qrels = load_qrels("qrels.txt")
bm25_results = evaluate_bm25(questions)

relevant_counts = count_relevant_per_query_list(bm25_results, qrels)
prec = calc_precission(relevant_counts)
rec = calc_exahustividad(relevant_counts, qrels)
f1 = calc_f1(prec, rec)

print("Media Precision:", calc_media(prec))
print("Media Exhaustividad:", calc_media(rec))
print("Media F1:", calc_media(f1))

# Precision
peores_prec, mejores_prec = seleccionar_extremos(prec, n=5)
print("\nPeores 5 en Precision:", peores_prec)
print("Mejores 5 en Precision:", mejores_prec)

# Exhaustividad
peores_rec, mejores_rec = seleccionar_extremos(rec, n=5)
print("\nPeores 5 en Exhaustividad:", peores_rec)
print("Mejores 5 en Exhaustividad:", mejores_rec)

# F1
peores_f1, mejores_f1 = seleccionar_extremos(f1, n=5)
print("\nPeores 5 en F1:", peores_f1)
print("Mejores 5 en F1:", mejores_f1)