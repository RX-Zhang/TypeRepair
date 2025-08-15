public Iterator<Chromosome> iterator() {
    final Iterator<Chromosome> baseIterator = getChromosomeList().iterator();
    return new Iterator<Chromosome>() {
        private boolean canRemove = false;

        @Override
        public boolean hasNext() {
            return baseIterator.hasNext();
        }

        @Override
        public Chromosome next() {
            canRemove = true;
            return baseIterator.next();
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("next() has not been called before remove()");
            }
            baseIterator.remove();
            canRemove = false;
        }
    };
}
