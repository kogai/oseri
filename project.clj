(defproject oseri "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/core.match "0.3.0-alpha4"]
    [org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot oseri.core
  :target-path "target/%s"
  :plugins []
  :profiles {:uberjar {:aot :all}})
