public class RealNode extends Node {

    private float value;

    public RealNode(float inputValue) {
        value = inputValue;
    } 

    public float getValue() {
        return value;
    }

    public String ToString() {
        return "RealNode(" + value + ")";
    }
    
}
