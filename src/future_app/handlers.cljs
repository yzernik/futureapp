(ns future-app.handlers
  (:require
    [re-frame.core :refer [register-handler path trim-v after dispatch]]))

(def app-db {:greeting "Hello Clojure in iOS and Android! Yay!"
             :press-count 0})

(register-handler
  :initialize-db
  (fn [_ _]
    app-db))

(register-handler
  :set-greeting
  (fn [db [_ value]]
    (assoc db :greeting value)))

(register-handler
  :presser-pressed
  (fn [db [_ _]]
    (println "presser pressed...")
    (update-in db [:press-count] inc)))
