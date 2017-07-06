(ns oseri.core-test
  (:require [clojure.test :refer :all]
            [oseri.core :refer :all]
            [oseri.view :refer [show-board]]
            [oseri.model-test :refer [create-board-from-str]]))

(deftest test-initial-board
  (testing "should initialize board"
    (let [
      actual (initial-board 8)
      expect (create-board-from-str '(
        "        "
        "        "
        "        "
        "   BW   "
        "   WB   "
        "        "
        "        "
        "        "
        ))
    ]
    (is (= expect actual)))))
