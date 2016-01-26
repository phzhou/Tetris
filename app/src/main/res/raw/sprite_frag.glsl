precision mediump float;
uniform sampler2D u_Texture;
uniform vec4 vColor;
varying vec2 v_TexCoordinate;
void main() {
  // gl_FragColor = vec4(v_TexCoordinate[1], v_TexCoordinate[0], 1, 1);
  gl_FragColor = vColor * texture2D(u_Texture, v_TexCoordinate);
}