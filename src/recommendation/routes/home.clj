(ns recommendation.routes.home
  (:require[compojure.core :refer :all]
           [recommendation.views.layout :as layout])
  (:import  [java.io File]
           [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.neighborhood ThresholdUserNeighborhood]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity]
           [org.apache.mahout.cf.taste.impl.recommender GenericUserBasedRecommender]))



(defn recommendation [user number fileName]
  (let [model (new FileDataModel (new File fileName))
        similarity (new PearsonCorrelationSimilarity model)
        neighborhood (new ThresholdUserNeighborhood 0.1 similarity model)
        recommender (new GenericUserBasedRecommender model neighborhood similarity)]

    (def data-list (.recommend recommender user number))))

(defn prepare-item-data[item]
   (clojure.string/join  ["<item id= \"" (.getItemID item) "\" value=\"" (.getValue item) "\" />"]))


(defn prepare-xml-data[lista]
 (map #(prepare-item-data %)
       lista))



(defn prepare-xml[lista] 
 (str  "<?xml version=\"1.0\" encoding=\"utf-8\"?><items>" (clojure.string/join (prepare-xml-data lista)) "</items>"))




;; test

;(println (prepare-xml data-list))

;(defn parse-int [s]
;   (Integer. (re-find  #"\d+" s )))



(defn home [user number file] (do (recommendation (Integer. user) (Integer. number) file )
               (prepare-xml data-list)))

(defroutes home-routes
  (GET "/home" [user number file] (home user number file)))




