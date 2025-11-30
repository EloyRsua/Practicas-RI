import bm25s
import Stemmer

retriever = bm25s.BM25.load("pizzas-es", load_corpus=True)

queries = {
    "capricciosa": "mozzarella tomate alcachofas champiñones prosciutto aceitunas huevo",
    "marinara": "tomate ajo orégano aceite de oliva",
    "quattro formaggi": "mozzarella gorgonzola fontina stracchino",
    "romana": "tomate anchoas orégano aceite de oliva",
    "viennese": "tomate mozzarella salchicha aceite de oliva"
}

stemmer = Stemmer.Stemmer("spanish")

for query_id, query_text in queries.items():
    query_tokenized = bm25s.tokenize(query_text, stemmer=stemmer, stopwords="es", lower=True, show_progress=False)

    results, scores = retriever.retrieve(query_tokenized, k=3, corpus=retriever.corpus, show_progress=False)

    print(f"\nMejores resultados para la consulta \"{query_id}\":")

    for rank in range(results.shape[1]):
        idx = rank
        result = results[0, idx]
        score = scores[0, idx]

        print(f"\t{rank+1}. {score:.4f}\t{result['text'][:80]}")