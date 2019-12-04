package a8;

import JSpotBoard.Position;
import JSpotBoard.Spot;
import JSpotBoard.SpotListener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Controller implements SpotListener {
	static int delay = 100;
	static JPanel top_panel = new JPanel();
	static View view = new View();
	private static boolean isContinue = true;
	private static boolean has = false;
	static Thread thread;

		view = v;
	}

	static void setPanel(JPanel p) {
		top_panel = p;
	}

	static void nextStep() {
		Model.nextState();
		view.update();
	}

	@Override
	public void spotClicked(Spot s) {
		boolean isAlive = Model.getCellAlive(s);
		Model.setCellAlive(s, !isAlive);
		view.update();
	}

	@Override
	public void spotEntered(Spot s) {
		s.highlightSpot();
	}

	@Override
	public void spotExited(Spot s) {
		s.unhighlightSpot();
	}

	public static void reset(ActionEvent e) {
		String[] xAndYSize = view.sizeField.getText().split("\\*");
		if (xAndYSize.length != 2) {
			view.warning.setVisible(true);
			return;
		}
		int xSize, ySize;
		try {
			xSize = Integer.parseInt(xAndYSize[0]);
			ySize = Integer.parseInt(xAndYSize[1]);

		} catch (NumberFormatException E) {
			view.warning.setVisible(true);
			return;
		}
		if (xSize > 500 || xSize < 10 || ySize > 500 || ySize < 10) {
			view.warning.setVisible(true);
			return;
		}

		Model.width = xSize;
		Model.height = ySize;

		Model.cells = new boolean[xSize][ySize];
		isContinue = false;
		top_panel.remove(view);
		view = new View();
		top_panel.add(view, BorderLayout.CENTER);
		top_panel.revalidate();
	}

	public static void setPosAndSta(ActionEvent e) {
		String position = view.posText;
		String stateString = view.stateText;
		Supplier<Boolean> state = () -> {
			if (stateString.equals("f"))
				return false;
			if (stateString.equals("t"))
				return true;

			return new Random().nextBoolean();
		};

		if (position.equals("")) {
			Model.allPositions.get().forEach(position1 -> Model.cells[position1.x][position1.y] = state.get());
		} else {
			try {
				Position position1 = Position.Parse(position);
				Model.cells[position1.x][position1.y] = state.get();
			} catch (IllegalArgumentException E) {
			}
		}

		view.update();
	}

	public static void setOther(ActionEvent e) {
		try {
			int delay = Integer.parseInt(view.delayField.getText());
			if (delay >= 0 && delay <= 1000)
				Controller.delay = delay;
		} catch (IllegalArgumentException E) {
		}

		String torus = view.torusField.getText();
		if ("t".equals(torus) || "T".equals(torus))
			Model.torus = true;
		if ("f".equals(torus) || "F".equals(torus))
			Model.torus = false;

		String lowStirng = view.lowField.getText();
		String highString = view.highField.getText();

		boolean lowCanParse = validIntFormat(lowStirng);
		boolean highCanParse = validIntFormat(highString);

		if (lowCanParse && highCanParse) {
			int low = Integer.parseInt(lowStirng);
			int high = Integer.parseInt(highString);
			if (low > 0 && high > 0 && low <= high) {
				Model.low = low;
				Model.high = high;
			}
		} else {
			if (lowCanParse) {
				int low = Integer.parseInt(lowStirng);
				if (low > 0 && low <= Model.high)
					Model.low = low;
			} else {
				int high = Integer.parseInt(highString);
				if (high > 0 && high >= Model.low)
					Model.high = high;
			}
		}
	}

	static void nextStep(ActionEvent e) {
		isContinue = false;
		nextStep();
	}

	public static void _continue(ActionEvent actionEvent) {
		isContinue = false;
		while (thread != null && thread.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isContinue = true;
		thread = new Thread(() -> {
			while (isContinue) {
				nextStep();
				if (isContinue)
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		});
		thread.start();
	}

	public static void suspend(ActionEvent actionEvent) {
		isContinue = false;
	}

	public static boolean validIntFormat(String s) {
		try {
			Integer.parseInt(s);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}
