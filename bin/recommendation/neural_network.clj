(ns recommendation.neural-network
  (:require recommendation.game-category)
   (:import [fi.foyt.foursqare.myapi ReadClues]
            [main GeostepNeuralNetwrok]))


(defn get-clues-categories[game-id] (conj (recommendation.game-category/get-my-category game-id) "" game-id ))

(defn categories-count[game-id] (.returnCategoriesCount (new ReadClues) (into-array String (get-clues-categories game-id))))

(defn get-vector[text] (clojure.string/split  (clojure.string/replace (str text) #",\n" "")  #","))

(defn sum-categories [text] (reduce + (map #(read-string %) (pop (pop (get-vector text))))))

(defn conj*
     [s x y]
     (conj (vec s) x y))

(defn calculate-categories [text] (let [sum (sum-categories text)
                                        vector (get-vector text)]
                                    (conj*
                                      (map #(double (/ (read-string %) sum)) (pop (pop vector)))
                                      (double (read-string (peek (pop vector))))
                                      (double (/ (read-string (peek  vector)) 1500)))))

(defn call-get-result[game-id] (let [network (new GeostepNeuralNetwrok)]
                                 (.getResult  network (into-array Double (calculate-categories (categories-count game-id))))))

(defn get-relevant[game-id] (if (> (call-get-result "142670") 0.75)
                              "relevant"
                              "irrelevant"))