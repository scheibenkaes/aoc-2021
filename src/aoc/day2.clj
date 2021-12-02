(ns aoc.day2
  (:require [aoc.core :as aoc]))

(defonce ship-data {:horizontal   0
                    :depth        0
                    :instructions []})

(defmulti apply-instruction (fn [inst ship] (first inst)))

(defmethod apply-instruction :forward
  [[_ value] ship]
  (update-in ship [:horizontal] + value))

(defmethod apply-instruction :up
  [[_ value] ship]
  (update-in ship [:depth] - value))

(defmethod apply-instruction :down
  [[_ value] ship]
  (update-in ship [:depth] + value))

(defn calc-mult
  [ship]
  (assoc ship :mult (* (get ship :depth 0) (get ship :horizontal 0))))

(defn solve
  [{:keys [instructions] :as ship}]
  (if-let [inst (first instructions)]
    (let [remaining (drop 1 instructions)
          ship*     (apply-instruction inst ship)]
      (recur (assoc ship* :instructions remaining)))
    (calc-mult ship)))

(defn prepare-ship
  [instructions]
  (assoc ship-data :instructions instructions))

(defn run-on-test-file!
  []
  (->> 2
       aoc/load-input-as-instructions
       prepare-ship
       solve))

;; ----------------- pt 2
