(ns ^:figwheel-hooks fallout-solver.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]))

(defn fallout-solver []
  [:div "hello world"])

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
