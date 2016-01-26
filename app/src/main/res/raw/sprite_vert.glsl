uniform mat4 uMVPMatrix;
attribute vec4 vPosition;
attribute vec2 a_TexCoordinate;
varying vec2 v_TexCoordinate;
void main() {
  // The matrix must be included as a modifier of gl_Position.
  // Note that the uMVPMatrix factor *must be first* in order
  // for the matrix multiplication product to be correct.
  gl_Position = uMVPMatrix * vPosition;
  v_TexCoordinate = a_TexCoordinate;
}
