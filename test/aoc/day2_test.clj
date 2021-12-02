(ns aoc.day2-test
  (:require [clojure.test :refer :all]
            [aoc.day2 :as sut]))

(def test-input [[:forward 5]
                 [:down 5]
                 [:forward 8]
                 [:up 3]
                 [:down 8]
                 [:forward 2]])

(deftest solve-test
  (testing "test input leads to output"
    (let [{:keys [depth horizontal mult]} (sut/solve (sut/prepare-ship test-input))]
      (is (= mult 150))
      (is (= depth 10))
      (is (= horizontal 15)))))

(deftest ^:integration solve-on-input-file
  (let [result (sut/run-on-test-file!)]
    (is (= 1855814 (:mult result)))))
