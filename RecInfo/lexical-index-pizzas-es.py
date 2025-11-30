import bm25s
import Stemmer

corpus = [
    "Aunque puede parecer extraña para una pizza italiana por incluir un ingrediente poco común—la salchicha de Viena, la pizza vienesa es, en realidad, una variedad bien conocida de pizza napolitana. Suele prepararse con una base de salsa de tomate, cubierta con mozzarella y rodajas de salchicha de Viena (wurstel). Originaria de Nápoles, es especialmente popular entre los niños, que a menudo son sus mayores aficionados. Una variante común incluye también patatas fritas como ingrediente adicional. Aunque los puristas de la pizza pueden no considerarla una de las recetas clásicas, la pizza vienesa sigue siendo una opción familiar y apreciada en muchas pizzerías del sur de Italia.",
    "La pizza caprichosa es un tipo de pizza de la cocina italiana elaborada con queso mozzarella, jamón cocido italiano, champiñones, alcachofa y tomate. Entre los tipos de setas comestibles que se pueden utilizar se incluyen los cremini (champiñones blancos) y otros. Algunas versiones también pueden llevar prosciutto (jamón curado en seco), corazones de alcachofa marinados, aceite de oliva, aceitunas, hojas de albahaca e incluso huevo. Algunas variantes se preparan con una base fina. Aunque comparte ingredientes similares con la pizza cuatro estaciones, la diferencia radica en que en la caprichosa los ingredientes se disponen de forma distinta.",
    "La pizza marinera, o a la marinera, es una pizza napolitana típica aderezada con salsa de tomate, aceite de oliva virgen extra, orégano y ajo. Se considera la pizza más antigua elaborada con una base de tomate.",
    "La pizza cuatro quesos es una variedad de pizza de la cocina italiana que se cubre con una combinación de cuatro tipos de queso, normalmente fundidos entre sí, y que puede prepararse con salsa de tomate (roja) o sin ella (blanca). Es una pizza popular en todo el mundo, incluida Italia, y constituye uno de los platos más emblemáticos de los menús de las pizzerías.",
    "La pizza Romana es un estilo clásico de pizza italiana que comparte algunos ingredientes con la pizza napolitana —como el tomate, la mozzarella, las anchoas, el orégano y el aceite de oliva virgen extra—, pero se distingue por su masa más fina y crujiente, característica de la tradición romana. En Nápoles, sin embargo, el nombre pizza Romana suele referirse a una pizza napolitana con anchoas, mientras que en otras regiones de Italia el mismo tipo de pizza puede denominarse pizza Napoli o napolitana. A pesar de las similitudes en los ingredientes, ambos estilos representan dos tradiciones pizzeras distintas dentro de la gastronomía italiana."
]

stemmer = Stemmer.Stemmer("spanish")

corpus_tokenized = bm25s.tokenize(
    corpus,
    stopwords="es",
    stemmer=stemmer,
    lower=True,
    show_progress=True
)

retriever = bm25s.BM25(method="lucene", idf_method="lucene")
retriever.index(corpus_tokenized, show_progress=True)

retriever.save("pizzas-es", corpus=corpus)