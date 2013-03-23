(ns spit-site.helpers
  (:use [clojure.java.io :only (file delete-file)]
        [clojure.string :only (join split)]))

(defn relativize
  "Takes two files and returns the relative path between them"
  [base-file path-file]
  (let [uri-base (.toURI base-file)
        uri-path (.toURI path-file)
        relative-path (.getPath (.relativize uri-base uri-path))]
    (if (.equals relative-path (.getAbsolutePath path-file))
      nil ; Was not relative to the base
      (file relative-path))))

(defn path-of
  "Extract the directory component(s) of a file path"
  [the-file]
  (let [sep java.io.File/separator
        file-path (.getPath the-file)
        only-path (join sep (butlast (split file-path (re-pattern sep))))]
    (if (= 0 (count only-path)) nil only-path)))

; File utils

(defn wipe-dir
  "Clean out a given directory by removing all contained
  files and subfolders"
  ([root] (wipe-dir root false))

  ([root dry-run]
    (let [file-list (reverse (rest (file-seq (file root))))]
      (doseq [del-file file-list]
        (println (str (if dry-run "NOT " "") "Deleting " (.toString del-file)))
        (if-not dry-run (delete-file del-file))))))

(defn has-extension?
  "Predicate that determines if a given file has the given extension"
  [ext file]
  (.equals ext (-> (.getName file) (split #"\.") (last))))
