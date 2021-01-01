(ns ^:figwheel-hooks fallout-solver.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]))

(def state (atom {:input1 {:word "" :matches "0"}
                  :input2 {:word "" :matches "0"}
                  :input3 {:word "" :matches "0"}
                  :guesses ""}))

(defn distance [x y]
  (->> (map vector x y)
       (filter (partial apply =))
       count))

(defn matched? [{:keys [guesses] :as state} identifier]
  (when (not-empty guesses)
    (let [guess (last (clojure.string/split-lines guesses))
          {:keys [word matches]} (identifier state)]
      (when (not-empty word)
        (= (js/parseInt matches) (distance word guess))))))

(defn input-word [identifier]
  [:div
   [:input {:type "text"
            :placeholder "word"
            :style {:background-color
                    (when (matched? @state identifier) "green")}
            :value (get-in @state [identifier :word])
            :on-change (fn [event]
                         (swap! state assoc-in [identifier :word]
                                (-> event .-target .-value)))}]
   [:input {:type "text"
            :value (get-in @state [identifier :matches])
            :on-change (fn [event]
                         (swap! state assoc-in [identifier :matches]
                                (-> event .-target .-value)))
            :placeholder "match"}]])

(defn fallout-solver []
  [:div
   [:h1 "Fallout solver"]
   [:p "Usage: pick any 3 words, and add them to the table. Start inserting your guesses one by one, until all words are marked as green!"]
   (input-word :input1)
   (input-word :input2)
   (input-word :input3)

   [:textarea {:rows "10"
               :placeholder "guesses"
               :value (:guesses @state)
               :on-change (fn [event]
                            (swap! state assoc :guesses
                                   (-> event .-target .-value)))}]])

(defn get-app-element []
  (gdom/getElement "app"))

(defn mount [el]
  (rdom/render [fallout-solver] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(mount-app-element)

(defn ^:after-load on-reload []
  (mount-app-element))
