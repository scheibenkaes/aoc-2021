(ns aoc.day6-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day6 :as sut]))

(deftest day-test
  (let [fish           (sut/->fish 3)
        fish*          (sut/day fish)
        fish**         (sut/day fish*)
        fish***        (sut/day fish**)
        [fish**** & _] (sut/day fish***)]
    (is (= 2 (:timer fish*)))
    (is (= 1 (:timer fish**)))
    (is (= 0 (:timer fish***)))
    (is (= 6 (:timer fish****))))

  (testing "spawning of new fish"
    (let [fish              (sut/->fish 0)
          [fish* offspring] (sut/day fish)]
      (is (= fish* (sut/->fish 6)))
      (is (= offspring (sut/->fish 8))))))

(def test-data (clojure.string/split-lines
                "3,4,3,1,2
2,3,2,0,1
1,2,1,6,0,8
0,1,0,5,6,7,8
6,0,6,4,5,6,7,8,8
5,6,5,3,4,5,6,7,7,8
4,5,4,2,3,4,5,6,6,7
3,4,3,1,2,3,4,5,5,6
2,3,2,0,1,2,3,4,4,5
1,2,1,6,0,1,2,3,3,4,8
0,1,0,5,6,0,1,2,2,3,7,8
6,0,6,4,5,6,0,1,1,2,6,7,8,8,8
5,6,5,3,4,5,6,0,0,1,5,6,7,7,7,8,8
4,5,4,2,3,4,5,6,6,0,4,5,6,6,6,7,7,8,8
3,4,3,1,2,3,4,5,5,6,3,4,5,5,5,6,6,7,7,8
2,3,2,0,1,2,3,4,4,5,2,3,4,4,4,5,5,6,6,7
1,2,1,6,0,1,2,3,3,4,1,2,3,3,3,4,4,5,5,6,8
0,1,0,5,6,0,1,2,2,3,0,1,2,2,2,3,3,4,4,5,7,8
6,0,6,4,5,6,0,1,1,2,6,0,1,1,1,2,2,3,3,4,6,7,8,8,8,8"))

(defn ->line->fish [line] (->> (read-string (str "[" line "]"))
                               (map sut/->fish)))

(deftest days-test
  (let [i  (->line->fish "3,4,3,1,2")
        i2 (sut/days i 2)
        i3 (sut/days i 3)]
    (is (= '({:timer 1} {:timer 2} {:timer 1} {:timer 6} {:timer 8} {:timer 0})
           i2)))

  (testing "count after 18 days"
    (is (= 26
           (count (sut/days (->line->fish "3,4,3,1,2") 18)))))

  (testing "count after 80 days"
    (is (= 5934
           (count (sut/days (->line->fish "3,4,3,1,2") 80)))))
  (comment "do not run me :-D"
           (testing "count after 256 days"
             (is (= 26984457539
                    (count (sut/days (->line->fish "3,4,3,1,2") 256)))))))

(def test-file (first (aoc/load-input 6)))

(deftest test-file-test
  (let [fish (->line->fish test-file)]
    (is (= 350149
           (count (sut/days fish 80))))))
