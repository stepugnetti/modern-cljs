(defproject modern-cljs "0.1.0-SNAPSHOT"
  :description "A series of tutorials on ClojureScript"
  :url "https://github.com/magomimmo/modern-cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :pom-addition [:developers [:developer
                              [:id "magomimmo"]
                              [:name "Mimmo Cosenza"]
                              [:url "https://github.com/magomimmo"]
                              [:email "mimmo.cosenza@gmail.com"]
                              [:timezone "+2"]]]

  :min-lein-version "2.1.2"

  ;; clojure source code path
  :source-paths ["src/clj" "src/cljs" "src/brepl"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [compojure "1.2.1"]
                 [hiccups "0.3.0"]
                 [domina "1.0.3-SNAPSHOT"]
                 [org.clojars.magomimmo/shoreleave-remote "0.3.1-SNAPSHOT"
                  :exclusions [org.clojars.magomimmo/shoreleave-browser]]
                 [org.clojars.magomimmo/shoreleave-remote-ring "0.3.1-SNAPSHOT"
                  :exclusions [org.clojars.magomimmo/shoreleave-browser]]
                 [org.clojars.magomimmo/shoreleave-browser "0.3.2-SNAPSHOT"]
                 [org.clojars.stepugnetti/valip "0.4.0-SNAPSHOT"
                  :exclusions [com.keminglabs/cljx]]
                 [enlive "1.1.5"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]
            [lein-ring "0.8.8"]
            [com.keminglabs/cljx "0.4.0"]
            [com.cemerick/clojurescript.test "0.3.1"]]

  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "src/clj"
                   :rules :clj}
                  {:source-paths ["src/cljx"]
                   :output-path "src/cljs"
                   :rules :cljs}
                  {:source-paths ["test/cljx"] ;; cljx source dir
                   :output-path "target/test/clj" ;; clj output
                   :rules :clj} ;; clj generation rules

                  {:source-paths ["test/cljx"] ;; cljx source dir
                   :output-path "target/test/cljs" ;; cljs output
                   :rules :cljs}]}

  ;; enable cljsbuild tasks support
  :hooks [leiningen.cljsbuild
;;           cljx.hooks
          ]

  ;; ring tasks configuration
  :ring {:handler modern-cljs.core/app}

  :test-paths ["target/test/clj" "target/test/cljs"]

  ;; cljsbuild tasks configuration
  :cljsbuild {:builds
              {:ws-unit-tests
               {;; CLJS source code and unit test paths
                :source-paths ["src/brepl" "src/cljs" "target/test/cljs"]

                ;; Google Closure Compiler options
                :compiler {;; the name of emitted JS script file for unit testing
                           :output-to "test/js/testable_dbg.js"

                           ;; minimum optimization
                           :optimizations :whitespace
                           ;; prettyfying emitted JS
                           :pretty-print true}}

               :simple-unit-tests
               {;; same path as above
                :source-paths ["src/brepl" "src/cljs" "target/test/cljs"]

                :compiler {;; different JS output name for unit testing
                           :output-to "test/js/testable_pre.js"

                           ;; simple optimization
                           :optimizations :simple

                           ;; no need prettification
                           :pretty-print false}}

               :advanced-unit-tests
               {;; same path as above
                :source-paths ["src/cljs" "target/test/cljs"]

                :compiler {;; different JS output name for unit testing
                           :output-to "test/js/testable.js"

                           ;; advanced optimization
                           :optimizations :advanced

                           ;; no need prettification
                           :pretty-print false}}
               :dev
               {:source-paths ["src/brepl" "src/cljs"]

                ;; Google Closure Compiler options
                :compiler {;; the name of emitted JS script file
                           :output-to "resources/public/js/modern_dbg.js"

                           ;; minimum optimization
                           :optimizations :whitespace
                           ;; prettyfying emitted JS
                           :pretty-print true}}
               :pre-prod
               {:source-paths ["src/brepl" "src/cljs"]

                :compiler {;; different JS output name
                           :output-to "resources/public/js/modern_pre.js"

                           ;; simple optimization
                           :optimizations :simple

                           ;; no need prettification
                           :pretty-print false}}
               :prod
               {:source-paths ["src/cljs"]
                :compiler {;; different JS output name
                           :output-to "resources/public/js/modern.js"

                           ;; advanced optimization
                           :optimizations :advanced

                           ;; no need prettification
                           :pretty-print false}}}

              :test-commands {"phantomjs-whitespace"
                              ["phantomjs" :runner "test/js/testable_dbg.js"]

                              "phantomjs-simple"
                              ["phantomjs" :runner "test/js/testable_pre.js"]

                              "phantomjs-advanced"
                              ["phantomjs" :runner "test/js/testable.js"]}})
