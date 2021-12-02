(ns aoc.day2
  (:require [aoc.core :as aoc]))

(def ship-data {:aim          0
                :horizontal   0
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
  [interpreter {:keys [instructions] :as ship}]
  (if-let [inst (first instructions)]
    (let [remaining (drop 1 instructions)
          ship*     (interpreter inst ship)]
      (recur interpreter (assoc ship* :instructions remaining)))
    (calc-mult ship)))

(defn prepare-ship
  [instructions]
  (assoc ship-data :instructions instructions))

(defn run-on-test-file!
  []
  (->> 2
       aoc/load-input-as-instructions
       prepare-ship
       (solve apply-instruction)))

;; ----------------- pt 2

(defmulti apply-instruction2 (fn [inst ship] (first inst)))

(defmethod apply-instruction2 :forward
  [[_ value] ship]
  (-> ship
      (update-in [:horizontal] + value)
      (update-in [:depth] + (* value (:aim ship)) )))

(defmethod apply-instruction2 :up
  [[_ value] ship]
  (update-in ship [:aim] - value))

(defmethod apply-instruction2 :down
  [[_ value] ship]
  (update-in ship [:aim] + value))

(defn pt2-solve
  [instructions]
  (let []
    (solve apply-instruction2 (prepare-ship instructions))))

(defn run-pt2!
  ""
  []
  (let [instructions (aoc/load-input-as-instructions 2)]
    (pt2-solve instructions)))
