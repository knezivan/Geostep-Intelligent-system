(ns recommendation.training
   (:require recommendation.game-category)
   (:import [java.io FileWriter]
            [fi.foyt.foursqare.myapi ReadClues]))


(def games-for-training ["142670/1" "550643/1" "291201/1"
                         "971396/1" "789590/1" "733542/1" 
                         "463454/1" "710354/1" "314029/1" "239673/1" "342813/1" "822701/1" 
                         "591401/1" "414578/1" "706711/0" "789833/0" "312628/0" "603090/0"
                         "146841/0" "762494/0" "354248/1" "580098/1" "110783/1" "943178/0"
                         "359216/1" "617655/0" "338401/0" "682723/1" "423989/1" "847835/1" 
                         "416368/1" "122189/1" "818277/1" "390605/0" "196552/0" "903062/1" 
                         "304465/1" "961613/1" "716192/1" "911491/1" "273180/1" "124555/1" 
                         "204558/1" "448981/1" "311014/1" "903046/0" "876092/0" "737221/0" 
                         "969300/0" "630699/0"
                        ])


;;(defn get-games-numbers[] (map #(first %)  (map #(clojure.string/split  % #"/") games-for-training )))

;;(defn get-clues-categories[] (map #(conj (recommendation.game-category/get-my-category %) %) (get-games-numbers)))


(defn get-clues-categories[] (map #(conj (recommendation.game-category/get-my-category (first (clojure.string/split % #"/"))) (second (clojure.string/split % #"/")) (first (clojure.string/split % #"/")) ) games-for-training))

(def rez (get-clues-categories))

(defn categories-count[] (map #(.returnCategoriesCount (new ReadClues) (into-array String %)) (get-clues-categories)))

(defn prepare-for-file[] (apply str (categories-count)))

(defn create-file[name] (let [writer (new FileWriter name)]
                         (.append writer (prepare-for-file))
                          (.close writer)))


;;(def category-list (new ArrayList))
 
;;(defn create-list[categories] (map #(.add category-list %) categories))

;;(defn cat-numbers[categories] (do
                               ; (def category-list (new ArrayList))
                               ;; (create-list categories)
                              ;;  (.returnCategoriesCount (new ReadClues) category-list )))

;;(defn read-int-array[array] (map #(str %) array))

;;(def s "")


;;(defn prepare-item-data[item]
   ;;(def s (clojure.string/join  s (cat-numbers item))))

;;(defn prepare-data[] (map #(prepare-item-data %)  rez))

;;(defn create-file[name] (let [writer (new FileWriter name)]
                         ;; (.append writer s)
                         ;; (.close writer)))
                        ;;(map #(.append writer (read-int-array (cat-numbers %)))  (get-clues-categories))))

                        
                        


;(defn record-word-count [memo word]
 ; (assoc memo word (inc (memo word 0))))

;(defn count-words [words-to-count]
 ; (reduce record-word-count {} words-to-count))

;(defn count-categories[] (map #(count-words %) (get-clues-categories)))


;(def business 0)
;(def social 0)
;(def travel 0)
;(def irrelevant 0)

;(defn count-points (map #(if (= (first (first %)) "travel")
;           (def travel (+ travel (second (first %))))) ())

