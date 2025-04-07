package pl.youkidesign.TastyCode.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DrinkAdditions {

    LEMON("Lemon"),
    ICE("Ice cubes");

    final String label;

    @Override
    public String toString() {
        return this.label;
    }
}
