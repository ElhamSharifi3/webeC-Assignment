package rooms

class CalculatorController {

    def calc(double tec, double own) {
        double result = tec * own / 100
        render view: "CalculatorOutput", model: [result: result]
    }
}
