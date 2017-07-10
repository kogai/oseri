(ns oseri.core-test
  (:require [clojure.test :refer :all]
            [oseri.core :refer :all]
            [oseri.view :refer [show-board ->Tile]]
            [oseri.model-test :refer [create-board-from-str]]))

(deftest test-initial-board
  (testing "should initialize board"
    (let [actual (initial-board 8)
          expect (create-board-from-str '("        "
                                          "        "
                                          "        "
                                          "   BW   "
                                          "   WB   "
                                          "        "
                                          "        "
                                          "        "))]
      (is (= expect actual)))))

(deftest test-create-game
  (testing "should create game with closure"
    (let [actual ((create-game 8) (->Tile 4 2 :black))
          expect (create-board-from-str '("        "
                                          "        "
                                          "        "
                                          "   BW   "
                                          "  BBB   "
                                          "        "
                                          "        "
                                          "        "))]
      (is (= expect (first actual)))
      (is (= :white (second actual))))))

(deftest test-playable?
  (testing "should be able to determine that the board is playeable"
    (let [actual (playable? (create-board-from-str '("   "
                                                     " B "
                                                     "   ")) :black)
          expect false]
      (is (= expect actual)))
    (let [actual (playable? (create-board-from-str '("BWB"
                                                     "WBW"
                                                     "BWB")) :black)
          expect false]
      (is (= expect actual)))
    (let [actual (playable? (create-board-from-str '(" WB"
                                                     "WBW"
                                                     "BWB")) :black)
          expect true]
      (is (= expect actual)))))
