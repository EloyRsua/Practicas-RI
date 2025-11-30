import bm25s
import Stemmer

retriever = bm25s.BM25.load("pubmed", load_corpus=True)

while True:
    try:
        query = (query).strip()
        if query:
            break
        print("Query cannot be empty. Please try again.")
    except (KeyboardInterrupt, EOFError):
        query = None

stemmer = Stemmer.Stemmer("english")
query_tokenized = bm25s.tokenize(query, stemmer=stemmer, stopwords="en", lower=True)

results, scores = retriever.retrieve(query_tokenized, k=100, corpus=retriever.corpus, show_progress=False)

print("\nTop results:")

for rank in range(results.shape[1]):
    idx = rank
    result = results[0, idx]
    score = scores[0, idx]

    print(f"{rank+1}. {result['id']}\t{score:.4f}\t{result['title']}")